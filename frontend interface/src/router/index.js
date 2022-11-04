import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/PK/PkIndexView'
import RecordIndexView from '../views/Record/RecordIndexView'
import RecordContentView from '../views/Record/RecordContentView'
import RankedlistIndexView from '../views/Rank/RankedlistIndexView'
import UserBotIndexView from '../views/User/Bots/UserBotIndexView'
import NotFound from '../views/error/NotFoundView'
import UserAccountLoginView from '../views/User/account/UserAccountLoginView'
import UserAccountRegisterView from '../views/User/account/UserAccountRegisterView'
import store from "../store/index"

const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/PK/",
    meta: {
      requestAuth: true,
    }
  },

  {
    path: "/PK/",
    name: "pk_index",
    component: PkIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/Record/",
    name: "record_index",
    component: RecordIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/Record/:recordId/",
    name: "record_content",
    component: RecordContentView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/Rank/",
    name: "rank_index",
    component: RankedlistIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/User/Bots/",
    name: "user_bot_index",
    component: UserBotIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/User/account/login/",
    name: "user_account_login",
    component: UserAccountLoginView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/User/account/register/",
    name: "user_account_register",
    component: UserAccountRegisterView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/404/",
    name: "404",
    component: NotFound,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/"
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) =>{
  if(to.meta.requestAuth && !store.state.user.is_login){
    next({name: "user_account_login"});
  } else{
    next();
  }
})

export default router
