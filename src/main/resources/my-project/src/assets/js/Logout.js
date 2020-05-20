import axios from 'axios';
import { Message } from 'element-ui';
import router from '../../router';
export default {
    name:"logout",
    methods:{
      logout(){
        axios.get("http://localhost:8080/user/logout").then((response)=>{
          let res = response.data;
          if(res.state == 1){
            Message.success("登出成功");
            window.sessionStorage.clear()
            router.push('/home')
          }
        })
      }
    }
}

