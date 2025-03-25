<script setup>
import {ref} from "vue";
import axios from '@/util/http';

const form=ref({
    name:"",
    password:""
});




const handleSubmit=()=>{
        axios.post("/login",{
            name: form.value.name,
            password: form.value.password
        }).then(rs=>{
            if(rs.status==200){
                const token = rs.headers["authorization"]; // 헤더에서 토큰 가져오기
                localStorage.setItem("access_token",token)
                console.log("토큰:", token);

                alert("로그인성공")
            }


        }).catch(error=>{
            console.log(error)
            alert("로그인실패")
        })


}
</script>

<template>
    <div class="signup-container">
      <h2>로그인</h2>
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="id">아이디</label>
          <input v-model="form.name" type="text" id="id" required />
        </div>
  
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input v-model="form.password" type="password" id="password" required />
        </div>

  
        <button type="submit">로그인</button>
      </form>
    </div>
  </template>

<style scoped>
.signup-container {
  width: 300px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background: #f9f9f9;
}

h2 {
  text-align: center;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  width: 100%;
  padding: 10px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
}

button:hover {
  background: #0056b3;
}

.valid {
  color: green;
}

.invalid {
  color: red;
}
</style>