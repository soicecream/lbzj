import Vue from 'vue'
import Router from 'vue-router'
import workList from "@/views/oj/work/workList";
import work from "@/views/oj/work/work"
import adminindex from "@/views/administration/adminIndex";
import ranking from "@/views/oj/rank/ranking";
import user from "@/views/oj/user/user";
import adminwork from "@/views/administration/adminwork";
import adminProblem from "@/views/administration/adminProblem";
import adminuser from "@/views/administration/adminuser";

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
        meta: {title: 'Work-ID'},
        component: work
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
    ],
    hidden:true
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/404'),
  },
  {
    path:'/admin',
    name:'admin',
    component:adminindex,
    children: [
      {
        path:'/adminwork',
        name:'adminwork',
        component:adminwork,
      },
      {
        path: '/adminProblem',
        name:'adminProblem',
        component:adminProblem
      },
      {
        path: '/adminuser',
        name:'adminuser',
        component:adminuser
      }
        ]
  },
  {path: '*', redirect: '/404'}
]

const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const  router = createRouter()


export default router

