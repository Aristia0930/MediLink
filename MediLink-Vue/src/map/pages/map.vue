<template>
  <div id="map" ref="mapRef" class="map-container"></div>
</template>

<script setup>
/* eslint-disable no-unused-vars */
import { ref, onMounted, watch } from "vue";
import axios from '@/util/http';

const mapRef = ref(null);

// 위치 정보 저장용 ref
const positionObj = ref({ latitude: 36.1037824, longitude: 128.4210688 }); // 기본값 추가

const hospitalData = ref([]);

// 네이버 지도 API 로드 함수
const loadNaverMap = () => {
  return new Promise((resolve) => {
    if (window.naver) {
      resolve(window.naver);
      return;
    }

    const script = document.createElement("script");
    script.src =
      "https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=lamhj6mox4";
    script.async = true;
    script.defer = true;
    document.head.appendChild(script);

    script.onload = () => resolve(window.naver);
  });
};

// 현재 위치 받아오기
function accessToGeo(position) {
  positionObj.value.latitude = position.coords.latitude;
  positionObj.value.longitude = position.coords.longitude;
}

function askForLocation() {
  navigator.geolocation.getCurrentPosition(accessToGeo);
}

// 병원 데이터 가져오기
function getHospital() {
  axios.get(`/api/hos/gethospitals?x=128.4210688&y=36.1037824`).then(rs => {
    hospitalData.value = rs.data;
  }).catch(error => {
    console.error(error);
  });
}

// 마커를 보이게 함
function showMarker(map, marker) {
  if (marker.getMap()) return;
  marker.setMap(map);
}

// 마커를 숨김
function hideMarker(map, marker) {
  if (!marker.getMap()) return;
  marker.setMap(null);
}

// 마커 업데이트
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

// 병원 데이터 및 위치 정보가 모두 로드되었을 때 지도를 표시하고 마커를 생성하는 로직
const renderMapAndMarkers = async () => {
  const naver = await loadNaverMap();

  const map = new naver.maps.Map(mapRef.value, {
    center: new naver.maps.LatLng(positionObj.value.latitude, positionObj.value.longitude),
    zoom: 15,     // 초기 줌
    minZoom: 7,   // 최소 줌
    scaleControl: false,
    logoControl: false,
    mapDataControl: false,
    zoomControl: true,
  });

  // 마커 객체 배열로 저장
  const markers = hospitalData.value.map((data) => {
    const position = new naver.maps.LatLng(data.ypos, data.xpos); // ypos는 위도, xpos는 경도
    return new naver.maps.Marker({
      position: position,
      map: map,
      title: data.clCdNm,
      id: data.id,
      addr: data.addr
    });
  });

  // 지도 경계 설정
  var bounds = map.getBounds(),
    southWest = bounds.getSW(),
    northEast = bounds.getNE(),
    lngSpan = northEast.lng() - southWest.lng(),
    latSpan = northEast.lat() - southWest.lat();

  // 마커 보이기/숨기기
  naver.maps.Event.addListener(map, 'idle', function () {
    updateMarkers(map, markers);
  });
};

onMounted(() => {
  askForLocation();  // 위치 정보 요청
  getHospital();     // 병원 데이터 요청
});

watch([positionObj, hospitalData], async (newValues, oldValues) => {
  const [newPositionObj, newHospitalData] = newValues;

  // 위치 정보와 병원 데이터가 모두 로드되었을 때만 지도와 마커를 렌더링
  if (newPositionObj.latitude && newPositionObj.longitude && newHospitalData.length > 0) {
    console.log('데이터 변경 감지, 지도 및 마커 렌더링');
    await renderMapAndMarkers();
  }
}, { deep: true });
</script>

<style scoped>
#map {
  width: 100vw;
  height: 100vh;
  background-color: lightgray;
}
</style>
