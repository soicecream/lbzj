<template>
  <div style="background-color: rgb(238,238,238)">
    <NavBar></NavBar>
    <router-view></router-view>
  </div>
</template>
<script>
import NavBar from '../../components/oj/NavBar'
export default {
  name: 'oj-app',
  components: {
    NavBar
  },
  data(){
    return {

    }
  }
}
</script>


<style>
</style>

