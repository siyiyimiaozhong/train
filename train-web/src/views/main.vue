<template>
  <a-layout id="components-layout-demo-top-side-2">
    <header-view></header-view>
    <a-layout>
      <sider-view></sider-view>
      <a-layout-content
          :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script>
import { defineComponent } from 'vue';
import HeaderView from "@/components/header.vue";
import SiderView from "@/components/sider.vue";

export default defineComponent({
  name: "main-view",
  components: {
    SiderView,
    HeaderView,
  },
  setup() {
    return {
    };
  },
});
</script>
<style>

</style>
