<template>
  <el-menu :default-active="activeIndex" @select="handleSelect"
           style="height: 100%" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b"
           class="el-menu-demo"
           router>
    <el-menu-item>
      <div style="display:inline-block;font-size: 1.3rem">ZIME Online Judge</div>
    </el-menu-item>
    <el-menu-item index="/"><i class="el-icon-s-home"></i>主页</el-menu-item>
     <nav-bar-item
     v-for="item in topbarRouters"
     :item="item">
     </nav-bar-item>
  </el-menu>

</template>

<script>
import { mapGetters,mapMutation} from "vuex";
import NavBarItem from "@/components/admin/NavBarItem";
import store from "@/store/work";

export default {
  name: "adminNavBar",
  components:{NavBarItem},
  data() {
    return {
      activeIndex: "",
      zhi:null
    }
  },
  created() {

  },
  computed: {
    ...mapGetters(["topbarRouters"]),
  },
  methods: {
    a () {
      return store.getters.topbarRouters
    },
    handleSelect(key) {
      switch (key) {
        case '1':
          this.$router.push('/')
          break
        case '2-1':
          this.$router.push('/admin/problem/list')
          break
        case '2-2':
          this.$router.push('/admin/problem/create')
          break
        case '2-3':
          this.$router.push('/admin/tags/list')
          break
        case '3-1':
          this.$router.push('/admin/work')
          break
        case '4-1':
          this.$router.push('/admin/user')
          break
      }
    }
  }
}
</script>

<style scoped>

</style>
