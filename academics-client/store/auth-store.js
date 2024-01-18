import { defineStore } from "pinia";

export const useAuthStore = defineStore("authStore", () => {
    const token = ref(null)
    const user = ref(null)
    const userRole = ref(null)

    function logout() {
        token.value = null
        user.value = null
        userRole.value = null
        localStorage.removeItem('token');
        localStorage.removeItem('user');

        router.push('/')
        //redirect to login page



    }

    function getUser() {
        return user.value
    }

    return { token, user, userRole, logout, getUser }
})

