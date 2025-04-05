<template>
  <div id="map" ref="mapRef" class="map-container"></div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import axios from '@/util/http';

const mapRef = ref(null);
const positionObj = ref({ latitude: 36.1037824, longitude: 128.4210688 }); // 기본 위치
const hospitalData = ref([]);

const mapInstance = ref(null);        // ✅ 네이버 지도 객체 저장
const markerList = ref([]);           // ✅ 마커 리스트 저장

// 네이버 지도 API 로드
const loadNaverMap = () => {
  return new Promise((resolve) => {
    if (window.naver) {
      resolve(window.naver);
      return;
    }

    const script = document.createElement("script");
    script.src = "https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=lamhj6mox4";
    script.async = true;
    script.defer = true;
    document.head.appendChild(script);

    script.onload = () => resolve(window.naver);
  });
};

// 위치 접근
function accessToGeo(position) {
  positionObj.value.latitude = position.coords.latitude;
  positionObj.value.longitude = position.coords.longitude;
}

function askForLocation() {
  navigator.geolocation.getCurrentPosition(accessToGeo);
}

// 병원 데이터 가져오기
function getHospital() {
  // axios.get(`/api/hos/gethospitals?x=128.4210688&y=36.1037824`)
  axios.get(`/api/hos/gethospitals?x=${positionObj.value.latitude}&y=${positionObj.value.longitude}`)
    .then(rs => {
      hospitalData.value = rs.data;
    })
    .catch(error => {
      console.error(error);
    });
}

// 마커 표시/숨김 함수
function showMarker(map, marker) {
  if (!marker.getMap()) {
    marker.setMap(map);
  }
}

function hideMarker(map, marker) {
  if (marker.getMap()) {
    marker.setMap(null);
  }
}

// 마커 업데이트 (지도 경계 안에 있는 마커만 보이게)
function updateMarkers(map, markers) {
  const mapBounds = map.getBounds();
  for (const marker of markers) {
    if (mapBounds.hasLatLng(marker.getPosition())) {
      showMarker(map, marker);
    } else {
      hideMarker(map, marker);
    }
  }
}

// 지도만 먼저 렌더링
const renderMap = async () => {
  const naver = await loadNaverMap();

  mapInstance.value = new naver.maps.Map(mapRef.value, {
    center: new naver.maps.LatLng(positionObj.value.latitude, positionObj.value.longitude),
    zoom: 15,
    minZoom: 7,
    scaleControl: false,
    logoControl: false,
    mapDataControl: false,
    zoomControl: true,
  });

  // idle 이벤트로 마커 갱신
  naver.maps.Event.addListener(mapInstance.value, 'idle', () => {
    updateMarkers(mapInstance.value, markerList.value);
  });
};

// 마커만 렌더링
const renderMarkers = () => {
  if (!mapInstance.value || hospitalData.value.length === 0) return;

  // 기존 마커 제거
  markerList.value.forEach(marker => marker.setMap(null));

  markerList.value = hospitalData.value.map((data) => {
    const position = new naver.maps.LatLng(data.ypos, data.xpos);
    return new naver.maps.Marker({
      position,
      map: mapInstance.value,
      title: data.clCdNm,
      id: data.id,
      addr: data.addr
    });
  });

  // 초기에 한 번 마커 상태 업데이트
  updateMarkers(mapInstance.value, markerList.value);
};

// 마운트 시 위치 요청
onMounted(() => {
  askForLocation();
});

// 위치 변경 감지 시 지도 먼저 렌더링, 병원 데이터 요청
watch(positionObj, async (newVal) => {
  if (newVal.latitude && newVal.longitude) {
    await renderMap();
    getHospital();
  }
}, { deep: true });

// 병원 데이터 변경 시 마커만 렌더링
watch(hospitalData, () => {
  renderMarkers();
}, { deep: true });
</script>

<style scoped>
#map {
  width: 100vw;
  height: 100vh;
  background-color: lightgray;
}
</style>
