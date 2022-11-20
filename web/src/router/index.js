import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
export const constantRoutes = [
    {
        path: '/',
        redirect: '/home',
        component: () => import('@/views/oj/App'),
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('@/views/oj/home/Home'),
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
                component: () => import("@/views/oj/work/workList")
            },
            {
                path: '/work/:id',
                name: 'Work-ID',
                meta: {title: 'Work-ID'},
                component: () => import("@/views/oj/work/work")
            },
            {
                path: '/ranking',
                name: 'ranking',
                component: () => import("@/views/oj/rank/ranking")
            },
            {
                path: '/user/:id',
                name: 'user',
                component: () => import("@/views/oj/user/user")
            },
        ],
        hidden: true
    },
    {
        path: '/404',
        name: '404',
        component: () => import('@/views/share/404'),
    },
    {
        path: '/admin',
        name: 'admin',
        component: () => import("@/views/admin"),
        children: [
            {
                path: '/work',
                name: 'adminWork',
                component: () => import("@/views/admin/work/work"),
            },
            {
                path: '/problem',
                name: 'adminProblem',
                component: () => import("@/views/admin/problem/problem")
            },
            {
                path: '/user',
                name: 'adminUser',
                component: () => import("@/views/admin/user/user")
            }
        ]
    },

    {path: '/login', name: 'login', component: () => import('@/views/share/loginRegister')},

    {path: '*', redirect: '/404'},


]

const createRouter = () => new Router({
    mode: 'history', // require service support
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})

const router = createRouter()


export default router

