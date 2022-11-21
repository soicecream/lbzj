import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
export const constantRoutes = [
    {
        path: '/',
        redirect: '/home',
        component: () => import('@/views/oj/App'),
        children: [
            {path: '/home', name: 'home', component: () => import('@/views/oj/home/Home'),},
            {path: '/problem', name: 'problem-list', meta: {title: 'Problem List'}, component: () => import('@/views/oj/problem/problemList')},
            {path: '/problem/:id', name: 'problem-details', meta: {title: 'Problem Details'}, component: () => import('@/views/oj/problem/problem')},
            {path: '/problem/video/:id', name: 'problem-video', meta: {title: 'Problem Video'}, component: () => import('@/views/oj/problem/problemLearn')},
            {path: '/state', name: 'State', meta: {title: 'State'}, component: () => import('@/views/oj/state/state')},
            {path: '/work', name: 'Work', meta: {title: 'Work'}, component: () => import('@/views/oj/work/workList')},
            {
                path: '/work/:id', name: 'Work-ID', meta: {title: 'Work-ID'}, component: () => ('@/views/oj/work/work'),
                children: [
                    {path: '/workPro/:id', name: 'workPro', component: () => import('@/views/oj/work/workPro'),},
                    {path: '/workRank/:id', name: 'workRank', component: () => import('@/views/oj/work/workRank'),},
                    {path: '/workState/:id', name: 'workState', component: () => import('@/views/oj/work/workState'),}
                ]
            },
            {path: '/ranking', name: 'ranking', component: () => import('@/views/oj/rank/ranking')},
            {path: '/user/:id', name: 'user', component: () => import('@/views/oj/user/user')},
        ],
        hidden: true,
    },

    {
        path: '/admin', name: 'admin', component: () => import('@/views/admin/index'), redirect: '',
        children: [
            {path: 'work', name: 'adminWork', component: () => import('@/views/admin/work/work'),},
            {path: 'problem', name: 'adminProblem', component: () => import('@/views/admin/problem/problem'),},
            {path: 'user', name: 'adminUser', component: () => import('@/views/admin/user/user'),},
        ],
    },

    {path: '/404', name: '404', component: () => import('@/views/share/404'),},
    {path: '/login', name: 'login', component: () => import('@/views/share/loginRegister'),},

    {path: '*', redirect: '/404'}
]

const createRouter = () => new Router({
    mode: 'history', // require service support
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})

const router = createRouter()

import {getToken} from '@/utils/auth'


const whiteList = ['/login']

router.beforeEach((to, from, next) => {
    if (getToken()) {
        /* has token*/
        next()
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

