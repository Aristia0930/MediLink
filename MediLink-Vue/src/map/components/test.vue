<script setup>
import axios from '@/util/http';
import {ref} from "vue";

const tokenInput = ref("");
const location = ref([])

const saveToken=()=>{
    localStorage.setItem("access_token",tokenInput.value);
    alert("토큰이 저장됨")
    }


function userValue(){
    axios.get(`/api/user`).then(rs=>{
        console.log(rs.data)
    }).catch(error=>{
        console.error(error)
    })
}

function getHospital(){
    axios.get(`/api/hos/gethospitals?x=128.4210688&y=36.1037824`).then(rs=>{
        console.log(rs.data)
        location.value = rs.data
    }).catch(error=>{
        console.error(error)
    })
}

function check(){
    console.log(location.value.at(0).xpos)
    console.log(location.value.at(0).ypos)
}

</script>

<template>
  <div>
  <h1>테스트</h1>   

  <!-- 🔹 토큰 입력 필드 -->
  <input v-model="tokenInput" placeholder="토큰 입력하세요" />
    <!-- 🔹 토큰 저장 버튼 -->
  <button @click="saveToken">토큰 저장</button>  
  <button @click="userValue">유저정보 반환 번트 인증 필요</button> 
  <br>
  <button @click="getHospital">병원 정보 반환</button> 
  <button @click="check">병원 정보 확인</button> 
  </div>
</template>

<style scoped>

</style>