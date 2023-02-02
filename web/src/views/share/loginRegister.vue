<template>
  <div style="width: 100vw; height: 100vh; box-sizing: border-box;">
    <div class="contain">
      <div class="big-box" :class="{active:isLogin}">

        <!--        登录-->
        <div class="big-contain" key="bigContainLogin" v-if="isLogin">
          <div class="btitle">账户登录</div>
          <el-form :model="login_form" :rules="login_form_rules" ref="login_user_form"
                   style="width: 300px; height: 200px; margin-top: 5%;">
            <el-form-item prop="username">
              <el-input v-model="login_form.username" prefix-icon="el-icon-user" placeholder="用户名"></el-input>
            </el-form-item>

            <el-form-item prop="password">
              <el-input v-model="login_form.password" show-password prefix-icon="el-icon-lock"
                        placeholder="密码"></el-input>
            </el-form-item>

            <el-form-item style="text-align: center">
              <el-button @click="submit_login_form" class="bbutton" round>登录</el-button>
            </el-form-item>
          </el-form>
        </div>

        <!--        注册-->
        <div class="big-contain" key="bigContainRegister" v-else>
          <div class="btitle">创建账户</div>
          <el-form :model="register_form" :rules="register_form_rules" ref="register_user_form"
                   style="width: 300px; height: 350px; margin-top: 5%;">

            <el-form-item prop="username">
              <el-input v-model="register_form.username" class="w-50 m-2" placeholder="用户名"><i slot="prefix"
                                                                                               class="el-icon-user"/>
              </el-input>
            </el-form-item>

            <el-form-item prop="nickname">
              <el-input v-model="register_form.nickname" class="w-50 m-2" placeholder="昵称"><i slot="prefix"
                                                                                              class="el-icon-user"/>
              </el-input>
            </el-form-item>

            <el-form-item prop="password">
              <el-input v-model="register_form.password" class="w-50 m-2" placeholder="密码" type="password"
                        show-password><i slot="prefix" class="el-icon-lock"/></el-input>
            </el-form-item>

            <el-form-item prop="password_again">
              <el-input v-model="register_form.password_again" class="w-50 m-2" placeholder="再次输入密码" type="password"
                        show-password><i slot="prefix" class="el-icon-lock"/></el-input>
            </el-form-item>

            <el-form-item prop="phone">
              <el-input v-model="register_form.phone" class="w-50 m-2" placeholder="电话号码"><i slot="prefix"
                                                                                             class="el-icon-phone"/>
              </el-input>
            </el-form-item>

            <el-form-item prop="verification_code">
              <el-input v-model="register_form.verification_code" placeholder="验证码"
                        style="display: inline-block; width: 50%; vertical-align:top;">
                <i slot="prefix" class="el-icon-help"/>
              </el-input>
              <span @click="get_verification_code" style="height: 40px; float: right;"><VerificationCode
                  :identifyCode="identifyCode"/></span>
            </el-form-item>

            <el-form-item>
              <el-button @click="submit_register_form()" style="width: 100%" class="bbutton" round> 注册</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>

      <div class="small-box" :class="{active: isLogin}">
        <div class="small-contain" key="smallContainRegister" v-if="isLogin">
          <div class="stitle">你好，朋友!</div>
          <p class="scontent">开始注册，和我们一起旅行</p>
          <button class="sbutton" @click="changeType">注册</button>
        </div>

        <div class="small-contain" key="smallContainLogin" v-else>
          <div class="stitle">欢迎回来!</div>
          <p class="scontent">与我们保持联系，请登录你的账户</p>
          <button class="sbutton" @click="changeType">登录</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import VerificationCode from "@/components/share/verificationCode";
// import {setRoutes} from "@/router";
import {login} from "@/api/user";
import {setToken} from "@/utils/auth";

export default {
  name: 'loginRegister',

  components: {
    VerificationCode,

  },

  data() {
    return {
      // 是否位登录页面
      isLogin: true,

      // 用户登录及注册的数据和判定规则
      // login_form: {username: "", password: "",},
      login_form: {username: "admin", password: "123456",},
      login_form_rules: {
        username: [{required: true, message: '请填写用户名', trigger: 'blur'},],
        password: [{required: true, message: '请填写密码', trigger: 'blur'},],
      },
      register_form: {username: "", nickname: "", password: "", password_again: "", phone: "", verification_code: ""},
      register_form_rules: {
        username: [
          {required: true, message: '请填写用户名', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        nickname: [
          {required: true, message: '请填写昵称', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请填写密码', trigger: 'blur'},
          {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
        ],
        password_again: [
          {required: true, message: '请再次填写密码', trigger: 'blur'},
          {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请填写电话号码', trigger: 'blur'},
          {min: 11, max: 11, message: '请填写11位的电话号码', trigger: 'blur'},
          {pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: "请输入正确的电话号码",}
        ],
        verification_code: [
          {required: true, message: '请填写验证码', trigger: 'blur'},
          {min: 4, max: 4, message: '请填写4为验证码', trigger: 'blur'}
        ],
      },

      // 图片验证码
      identifyCode: '',
      // 验证码规则
      identifyCodes: '3456789abcdefghjkmnpqrstuvwxyABCDEFGHJKMNPQRSTUVWXY',
    }
  },

  methods: {
    // 清空所有数据
    clear_data() {
      if (this.$refs.login_user_form !== undefined)
        this.$refs.login_user_form.resetFields()
      if (this.$refs.register_user_form !== undefined)
        this.$refs.register_user_form.resetFields()
    },

    // 登录注册页面交换
    changeType() {
      if (this.isLogin)
        this.get_verification_code()
      this.isLogin = !this.isLogin
      this.clear_data()
    },


    // 提交登录 或者 注册的
    submit_login_form() {
      this.$refs.login_user_form.validate((valid) => {
        if (valid) {
        login(this.login_form).then(res=>{
          setToken(res.data)
          console.log(res.data)
          this.$router.push("/home").catch()
        })
        }
      });
    },

    submit_register_form() {
      this.$refs.register_user_form.validate((valid) => {
        if (valid) {
          let form = this.register_form
          if (form.verification_code.toLowerCase() !== this.identifyCode.toLowerCase()) {
            this.$message.warning("验证码错误")
            form.verification_code = ""
            return false
          }
          if (form.password !== form.password_again) {
            this.$message.warning("两个密码不一样，请确认后重试")
            return false
          }

          this.$message.info("登录吧孩子，你没有权限注册！！")
          this.isLogin = true
          // this.get_verification_code()
          // this.request.post("/user/register", form).then(res => {
          //   console.log(res)
          //   if (res.code === "200") {
          //     this.$message.success("注册成功请登录")
          //     this.isLogin = true
          //     this.$message.success("登陆成功")
          //   } else {
          //     this.$message.error(res.message)
          //   }
          // })
        } else {
          this.$message.error("请确认输入")
          return false;
        }
      });
    },

    // 获取验证码
    get_verification_code() {
      this.identifyCode = ''
      for (let i = 0; i < 4; i++) {
        this.identifyCode += this.identifyCodes[Math.floor(Math.random() * (this.identifyCodes.length - 0) + 0)]
      }
      // console.log(this.identifyCode)
    },

  }
}
</script>

<style scoped="scoped">
.contain {
  width: 60%;
  height: 60%;
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  border-radius: 20px;
  box-shadow: 0 0 3px #f0f0f0, 0 0 6px #f0f0f0;
}

.big-box {
  width: 70%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 30%;
  transform: translateX(0%);
  transition: all 1s;
}

.big-contain {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.btitle {
  font-size: 1.5em;
  font-weight: bold;
  color: #ff5252cc;
}

.bbutton {
  width: 100%;
  background-color: #ff5252cc;
  color: #fff;
}

.small-box {
  width: 30%;
  height: 100%;
  background: linear-gradient(45deg, #fad0c4, #ff5252cc);
  position: absolute;
  top: 0;
  left: 0;
  transform: translateX(0%);
  transition: all 1s;
  border-top-left-radius: inherit;
  border-bottom-left-radius: inherit;
}

.small-contain {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.stitle {
  font-size: 1.5em;
  font-weight: bold;
  color: #fff;
}

.scontent {
  font-size: 0.8em;
  color: #fff;
  text-align: center;
  padding: 2em 4em;
  line-height: 1.7em;
}

.sbutton {
  width: 60%;
  height: 40px;
  border-radius: 24px;
  border: 1px solid #fff;
  outline: none;
  background-color: transparent;
  color: #fff;
  font-size: 0.9em;
  cursor: pointer;
}

.big-box.active {
  left: 0;
  transition: all 0.5s;
}

.small-box.active {
  left: 100%;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
  border-top-right-radius: inherit;
  border-bottom-right-radius: inherit;
  transform: translateX(-100%);
  transition: all 1s;
}
</style>
