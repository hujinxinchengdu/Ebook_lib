import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';

//配置插件,所有插件都需要在这里配置
//看到#就要想到选择器 vue cli 初始执行main.ts, 将内容页app.vue渲染到index.html,完成页面显示
createApp(App).use(store).use(router).use(Antd).mount('#app');
