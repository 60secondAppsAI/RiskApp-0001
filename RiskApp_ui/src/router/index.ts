import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import RiskProfiles from '../components/RiskProfiles.vue';
import RiskProfileDetail from '../components/RiskProfileDetail.vue';
import RiskLimits from '../components/RiskLimits.vue';
import RiskLimitDetail from '../components/RiskLimitDetail.vue';
import RiskMeasurements from '../components/RiskMeasurements.vue';
import RiskMeasurementDetail from '../components/RiskMeasurementDetail.vue';
import RiskBreachs from '../components/RiskBreachs.vue';
import RiskBreachDetail from '../components/RiskBreachDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/riskProfiles',
    name: 'RiskProfiles',
    component: RiskProfiles
  },
  {
    path: '/riskProfile/:id',
    name: 'RiskProfileDetail',
    component: RiskProfileDetail,
    props: true
  },
  {
    path: '/riskLimits',
    name: 'RiskLimits',
    component: RiskLimits
  },
  {
    path: '/riskLimit/:id',
    name: 'RiskLimitDetail',
    component: RiskLimitDetail,
    props: true
  },
  {
    path: '/riskMeasurements',
    name: 'RiskMeasurements',
    component: RiskMeasurements
  },
  {
    path: '/riskMeasurement/:id',
    name: 'RiskMeasurementDetail',
    component: RiskMeasurementDetail,
    props: true
  },
  {
    path: '/riskBreachs',
    name: 'RiskBreachs',
    component: RiskBreachs
  },
  {
    path: '/riskBreach/:id',
    name: 'RiskBreachDetail',
    component: RiskBreachDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
