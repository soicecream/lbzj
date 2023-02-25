import Vue from 'vue'
import Router from 'vue-router'
import workList from "@/views/oj/work/workList";
import work from "@/views/oj/work/work"
import ranking from "@/views/oj/rank/ranking";
import user from "@/views/oj/user/user";
import store from "@/store/work";
import {removeToken} from "@/utils/auth";

Vue.use(Router)
export const constantRoutes = [
  {
    path: '/',
    redirect: '/home',
    component: () => import('@/views/oj/App'),
    children: [
      {
        path: '/home',
        name:'home',
        component:() => import('@/views/oj/home/Home'),
      },
      {
        path: '/problem',
        name: 'problem-list',
        meta: {title: 'Problem List'},
        component: () => import('@/views/oj/problem/problemList')
      },
      {
        path: '/problem/:id',
        name: 'problem-details',
        meta: {title: 'Problem Details'},
        component: () => import('@/views/oj/problem/problem')
      },
      {
        path: '/problem/video/:id',
        name: 'problem-video',
        meta: {title: 'Problem Video'},
        component: () => import('@/views/oj/problem/problemLearn')
      },
      {
        path: '/state',
        name: 'State',
        meta: {title: 'State'},
        component: () => import('@/views/oj/state/state')
      },
      {
        path: '/work',
        name: 'Work',
        meta: {title: 'Work'},
        component: workList
      },
      {
        path: '/work/:id',
        name: 'Work-ID',
        redirect: '/workNotice/:id',
        meta: {title: 'Work-ID'},
        component: work,
        children: [
          {
            path:'/workNotice/:id',
            name:'workNotice',
            component: () => import('@/views/oj/work/worknotice'),
          },
          {
            path:'/workPro/:id',
            name:'workPro',
            component: () => import('@/views/oj/work/workPro'),
          },
          {
            path:'/workRank/:id',
            name:'workRank',
            component: () => import('@/views/oj/work/workRank'),
          },          {
            path:'/workState/:id',
            name:'workState',
            component: () => import('@/views/oj/work/workState'),
          }
       ]
      },
      {
        path:'/ranking',
        name:'ranking',
        component:ranking
      },
      {
        path:'/user/:id',
        name:'user',
        component:user
      },
      {
        path:'/userdata',
        name:'userdata',
        component: () => import('@/views/oj/user/userData'),
      },

      {
        path: '/admin/notice/list',
        name: 'admin-notice-list',
        component: () => import('@/views/admin/notice/list'),
      },

      {
        path: '/notice/list',
        name: 'notice-list',
        component: () => import('@/views/oj/notice/list')
      },
      {
        path: '/notice/:noticeId',
        name: 'notice-information',
        component: () => import('@/views/oj/notice/notice')
      }


    ],
    hidden:true
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/share/404'),
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/share/loginRegister'),
  },
]

const a404={
  path: '*',
  component: () => import('@/views/share/404'),
}

const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const  router = createRouter()

import { getToken } from '@/utils/auth'
import getter from "@/store/getters";


const whiteList = ['/login']

router.beforeEach((to, from, next) => {
  if (getToken()) {
    /* has token*/
    if (to.path === '/login') {
      next()
    }
    else {
      if(store.getters.roles.length===0) {
        store.dispatch('GetInfo').then(() => {
          store.dispatch('GenerateRoutes').then(accessRoutes => {
            // 根据roles权限生成可访问的路由表
            for(let i=0,length=accessRoutes.length;i<length;i++){
              router.addRoute(accessRoutes[i])
            }
            router.addRoute(a404)
             // 动态添加可访问路由表// hack方法 确保addRoutes已完成
            next({...to,replace:true})
          })
        })
      }
      else {
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login`) // 否则全部重定向到登录页
    }
  }
})

export default router

