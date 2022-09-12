import VueRouter from 'vue-router'
import Login from '../components/login/index.vue'
import Home from '../components/home/index.vue'
import UISourceData from '../components/uisourcedata'
import Bar from '../components/echarts/Bar.vue'
import YBar from '../components/echarts/YBar.vue'
import Area from '../components/echarts/Area.vue'
import Combination from '../components/echarts/Combination.vue'
import Line from '../components/echarts/Line.vue'
import Pie from '../components/echarts/Pie.vue'
import Radar from '../components/echarts/Radar.vue'
import Scatter from '../components/echarts/Scatter.vue'
import Scatter3D from '../components/echarts/Scatter3D.vue'
import UploadXlsOrXlsxFile from '../components/uploadxlsorxlsxfile/index.vue'
import UploadCSVOrTxtFile from '../components/uploadcsvortxtfile/index.vue'
import AddSourceData from '../components/addsourcedata/index.vue'
import ProcessedDatas from '../components/processeddatas/index.vue'
import SelectData from '../components/selectdata/index.vue'


const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
 

export default new VueRouter({
	routes:[
		{
			path:'/',
			component:Home
		},
		{
			path:'/home',
			component:Home,
			children:[
				{
					path:'login',
					component:Login
				},
				{
					path:'processeddatas',
					component:ProcessedDatas,
					children:[
						{
							path:'login',
							component:Login
						},
						{
							path:'addsourcedata',
							component:AddSourceData
						},
						{
							path:'uploadxlsorxlsxfile',
							component:UploadXlsOrXlsxFile							
						},
						{
							path:'uploadcsvortxtfile',
							component:UploadCSVOrTxtFile							
						},
						{
							path:'uisourcedata',
							component:UISourceData
						},
						{
							path:'bar',
							component:Bar
						},
						{
							path:'ybar',
							component:YBar
						},
						{
							path:'line',
							component:Line
						},
						{
							path:'area',
							component:Area
						},
						{
							path:'scatter',
							component:Scatter
						},
						{
							path:'combination',
							component:Combination
						},
						{
							path:'pie',
							component:Pie
						},
						{
							path:'radar',
							component:Radar
						},
						{
							path:'scatter3D',
							component:Scatter3D
						}			
					]
				},
				{
					path:'selectdata',
					component:SelectData
				},
			]
		},
	]
})