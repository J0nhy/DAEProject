import DashboardLayout from '../layout/DashboardLayout.vue'
// GeneralViews
import NotFound from '../pages/NotFoundPage.vue'

// Admin pages
import Overview from 'src/pages/Overview.vue'
import UserProfile from 'src/pages/UserProfile.vue'
import TableList from 'src/pages/TableList.vue'
import Typography from 'src/pages/Typography.vue'
import Icons from 'src/pages/Icons.vue'
import Maps from 'src/pages/Maps.vue'
import Notifications from 'src/pages/Notifications.vue'
import Upgrade from 'src/pages/Upgrade.vue'
import ManufacturerList from 'src/pages/ManufacturerList.vue'
import ManufacturerCreate from 'src/pages/ManufacturerCreate.vue'
import OrderList from 'src/pages/OrderList.vue'
import Login from 'src/pages/auth-test.vue'
import LogisticsOperatorList from 'src/pages/LogisticsOperator/LogisticsOperator.vue'
import LogisticsOperatorAdd from 'src/pages/LogisticsOperator/LogisticsOperatorAdd.vue'

const routes = [
  {
    path: '/',
    component: DashboardLayout,
    redirect: '/admin/overview'
  },
  {
    path: '/admin',
    component: DashboardLayout,
    redirect: '/admin/overview',
    children: [
      {
        path: 'overview',
        name: 'Overview',
        component: Overview
      },
      {
        path: 'user',
        name: 'User',
        component: UserProfile
      },
      {
        path: 'table-list',
        name: 'Table List',
        component: TableList
      },
      {
        path: 'login',
        name: 'login',
        component: Login
      },
      {
        path: 'manufacturer-list',
        name: 'Manufacturer List',
        component: ManufacturerList
      },
      {
        path: 'logisticsoperator-list',
        name: 'logisticsoperator List',
        component: LogisticsOperatorList
      },
      {
        path: 'logisticsoperator-add',
        name: 'logisticsoperator Add',
        component: LogisticsOperatorAdd
      },
      {
        path: 'order-list',
        name: 'Order List',
        component: OrderList
      },
      {
        path: 'manufacturer-create',
        name: 'Manufacturer Create',
        component: ManufacturerCreate
      },
      {
        path: 'typography',
        name: 'Typography',
        component: Typography
      },
      {
        path: 'icons',
        name: 'Icons',
        component: Icons
      },
      {
        path: 'maps',
        name: 'Maps',
        component: Maps
      },
      {
        path: 'notifications',
        name: 'Notifications',
        component: Notifications
      },
      {
        path: 'upgrade',
        name: 'Upgrade to PRO',
        component: Upgrade
      }
    ]
  },
  { path: '*', component: NotFound }
]

/**
 * Asynchronously load view (Webpack Lazy loading compatible)
 * The specified component must be inside the Views folder
 * @param  {string} name  the filename (basename) of the view to load.
function view(name) {
   var res= require('../components/Dashboard/Views/' + name + '.vue');
   return res;
};**/

export default routes
