<template>
<div>

    <el-menu-item  :index="item.path" v-if="hasOneShowingChild(item)">
      <template slot="title">
        <i :class=item.icon></i>
        <span>{{item.menuName}}</span>
      </template>
    </el-menu-item>


  <el-submenu v-else  :index="item.menuName" >
          <template slot="title">
            <i :class=item.icon></i>
            <span>{{item.menuName}}</span>
          </template>
    <el-menu-item-group>
    <nav-bar-item
        v-for="child in item.children"
        :key="child.path"
        :item="child"
    />
    </el-menu-item-group>
  </el-submenu>
</div>
</template>

<script>
export default {
  name: "NavBarItem",
  data() {
    return {
      activeIndex: "",
      zhi:false
    }
  },
  props: {
    // route object
    item: {
      type: Object,
      required: true
    },
  },
  methods:{
    hasOneShowingChild(item){
     if(item.children) return false
     else  return true;
    }
  }
}
</script>

<style scoped>

</style>
