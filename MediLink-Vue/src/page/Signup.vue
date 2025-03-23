<script setup>
import {ref} from "vue";
import axios from '@/util/http';

const form=ref({
    name:"",
    password:"",
    phone:"",
    age:null,
    address:"",
});

const idCheckMessage = ref("");
const isIdValid=ref(false);

const checkId= ()=>{
    if(!form.value.name){
        idCheckMessage.value="아이디를 입력해주세요"
        isIdValid.value=false

    }
    else{
        idCheckMessage.value=""

        axios.post("/public/idcheck",{username : form.value.name}).then(rs=>{
            if(rs.data==true){
                isIdValid.value=true
                idCheckMessage.value="사용가능한 아이디 입니다."
            }
            else{
                isIdValid.value=false
                idCheckMessage.value="중복된 아이디 입니다." 
            }
        }).catch(error=>{
            console.log(error)
        })

    }
}

const handleSubmit=()=>{
    if (isIdValid){
        axios.post("/public/join",{
            name: form.value.name,
            password: form.value.password,
            phone: form.value.phone,
            age:form.value.age,
            address:form.value.address
        }).then(rs=>{
            if(rs.status==200){
                alert("회원가입성공")
            }


        }).catch(error=>{
            console.log(error)
            alert("회원가입실패")
        })

    }
    else{
        alert("아이디 중복확인을 해주세요 ")
    }

}
</script>

<template>
    <div class="signup-container">
      <h2>회원가입</h2>
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="id">아이디</label>
          <input v-model="form.name" type="text" id="id" required />
          <button type="button" @click="checkId">아이디 중복 확인</button>
          <p v-if="idCheckMessage" :class="{ 'valid': isIdValid, 'invalid': !isIdValid }">
            {{ idCheckMessage }}
          </p>
        </div>
  
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input v-model="form.password" type="password" id="password" required />
        </div>
  
        <div class="form-group">
          <label for="phone">전화번호</label>
          <input v-model="form.phone" type="tel" id="phone" required />
        </div>
  
        <div class="form-group">
          <label for="age">나이</label>
          <input v-model="form.age" type="number" id="age" required />
        </div>
  
        <div class="form-group">
          <label for="address">주소</label>
          <input v-model="form.address" type="text" id="address" required />
        </div>
  
        <button type="submit">가입하기</button>
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