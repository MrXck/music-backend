(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6c1371e0"],{"519d":function(e,t,a){"use strict";a.r(t);var n=a("7a23");const c=e=>(Object(n["pushScopeId"])("data-v-a4362a12"),e=e(),Object(n["popScopeId"])(),e),o=c(()=>Object(n["createElementVNode"])("span",null,"歌手名",-1)),i=Object(n["createTextVNode"])("修改头像"),l=Object(n["createTextVNode"])(" 选择歌曲 "),r=Object(n["createTextVNode"])("提交");function d(e,t,a,c,d,s){const u=Object(n["resolveComponent"])("n-input"),p=Object(n["resolveComponent"])("n-space"),b=Object(n["resolveComponent"])("n-avatar"),g=Object(n["resolveComponent"])("n-button"),m=Object(n["resolveComponent"])("n-select");return Object(n["openBlock"])(),Object(n["createBlock"])(p,{vertical:"",align:"start"},{default:Object(n["withCtx"])(()=>[Object(n["createVNode"])(p,{vertical:"","item-style":"min-width: 200px"},{default:Object(n["withCtx"])(()=>[o,Object(n["createVNode"])(u,{value:c.singerDetail.name,"onUpdate:value":t[0]||(t[0]=e=>c.singerDetail.name=e),type:"text"},null,8,["value"])]),_:1}),Object(n["createVNode"])(p,{vertical:"",align:"center"},{default:Object(n["withCtx"])(()=>[Object(n["createVNode"])(b,{size:200,src:c.singerDetail.imgSrc,"fallback-src":c.notImage},null,8,["src","fallback-src"]),Object(n["createVNode"])(g,{onClick:c.chooseImg},{default:Object(n["withCtx"])(()=>[i]),_:1},8,["onClick"]),Object(n["createElementVNode"])("input",{ref:"file",type:"file",class:"hidden",onChange:t[1]||(t[1]=(...e)=>c.uploadImg&&c.uploadImg(...e))},null,544)]),_:1}),Object(n["createVNode"])(p,{vertical:"","item-style":"min-width: 200px"},{default:Object(n["withCtx"])(()=>[l,Object(n["createVNode"])(m,{filterable:"",multiple:"","onUpdate:value":[c.update,t[2]||(t[2]=e=>c.value=e)],value:c.value,options:c.options},null,8,["onUpdate:value","value","options"])]),_:1}),Object(n["createVNode"])(p,null,{default:Object(n["withCtx"])(()=>[Object(n["createVNode"])(g,{onClick:c.updateSingerDetail},{default:Object(n["withCtx"])(()=>[r]),_:1},8,["onClick"])]),_:1})]),_:1})}var s=a("f730"),u=a.n(s),p=a("c872"),b=a("b6e9"),g=a("9c03"),m=a("3519"),f=a("7baf"),j=a("73170"),O=a("6605"),v=a("b775"),h={name:"AdminUpdateSinger",components:{NSpace:p["a"],NInput:b["a"],NAvatar:g["a"],NButton:m["a"],NSelect:f["a"]},setup(){const e=Object(O["d"])();let t,a=Object(j["a"])(),c=Object(n["reactive"])({id:"",name:"",imgSrc:"",songs:[]}),o=[],i=Object(n["reactive"])([]),l=Object(n["reactive"])([]),r=e.query.id;function d(){if("image/png"!==t.files[0].type&&"image/jpeg"!==t.files[0].type)return a.error("只能上传png/jpeg格式的图片文件，请重新上传"),!1;if(!(t.files[0].size/1024/1024<2))return a.error("上传头像图片大小不能超过 2MB!"),!1;const e=new FormData;e.append("file",t.files[0]),v["a"].post("/file/uploadAvatar",e,{headers:{"Content-Type":"multipart/form-data"}}).then(e=>{console.log(e.data),0===e.data.code&&(c.imgSrc=e.data.data)})}function s(){t.click()}function p(){let e=[];for(let t=0;t<l.length;t++)e.push(o.find(e=>e.id===l[t]));v["a"].post("/admin/singer/update",c).then(e=>{0===e.data.code?a.success("修改成功"):a.error("修改失败")})}function b(e){l.length=0,l.push(...e)}return Object(n["onMounted"])(()=>{t=Object(n["getCurrentInstance"])().refs.file,void 0!==r&&v["a"].get("/singer/"+r).then(e=>{if(0===e.data.code){c.name=e.data.data.singer.name,c.id=e.data.data.singer.id,c.imgSrc=e.data.data.singer.imgSrc,o=e.data.data.songs;for(let e=0;e<o.length;e++)i.push({label:o[e].name+" 歌手:"+o[e].singers[0].name,value:o[e].id}),l.push(o[e].id)}})}),{notImage:u.a,singerDetail:c,options:i,value:l,chooseImg:s,uploadImg:d,update:b,updateSingerDetail:p}}},C=(a("7649"),a("6b0d")),N=a.n(C);const w=N()(h,[["render",d],["__scopeId","data-v-a4362a12"]]);t["default"]=w},"5ba6":function(e,t,a){},7649:function(e,t,a){"use strict";a("5ba6")}}]);
//# sourceMappingURL=chunk-6c1371e0.cfe26eb4.js.map