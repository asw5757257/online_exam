import Vue from 'vue';
import Router from 'vue-router';
import login from './pages/login';
import managerhome from './components/ManagerHome'
import welcome from './components/WelcomeManage';
import teacherList from './components/manage/teacherList'
import studentList from './components/manage/studentList'
import teacherAndMajor from './components/manage/TeacherAndMajor'
import majorList from './components/manage/MajorList'
import teacherhome from './components/TeacherHome'
import paperList from './components/teacher/paperList'
import questionList from './components/teacher/questionList'
import noticeList from './components/teacher/noticeList'
import home from './components/Home'
import studenthome from './components/StudentHome'
import exam from './components/student/exam'
import score from './components/student/score'
import resetPwd from './pages/resetPwd'
Vue.use(Router);

const router = new Router({
    routes:[
        {
            path:'/home',
            name:'home',
            component:home,
        },
        {
            path:'/resetPwd',
            name:'resetPwd',
            component:resetPwd,
        },
        {
            path:'/exam/:id',
            name:'exam',
            component:exam,
        },
        {
            path:'/score/:id/:answer/:thisAnswer',
            name:'score',
            component:score,
        },
        {
            path:'/login',
            name:'login',
            component:login,
            meta:{
                isLogin:false
            }
        },
        {
            path:'/managerHome',
            component:managerhome,
            redirect:'/welcome',
            children:[{
                path:'/welcome',
                component:welcome,
            },
            {
                path:'/teacher',
               
                component:teacherList
            },
            {
                path:'/student',
                component:studentList
            },
            {
                path:'/teacherAndMajor',
                component:teacherAndMajor
            },
            {
                path:'/major',
                component:majorList
            }

            ]
            
        },
        {
            path:'/teacherHome',
            name:'teacherHome',
            component:teacherhome,
            children:[{
                path:'/welcome',
                component:welcome,
            },
            {
                path:'/paper',
               
                component:paperList
            },
            {
                path:'/question',
                component:questionList
            },
            {
                path:'/notice',
                component:noticeList
            }

            ]
        },
        {
            path:'/studentHome',
            name:'studentHome',
            component:studenthome,
        }

        
        
    ]
})

export default router;