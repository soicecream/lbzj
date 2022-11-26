import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

const WorkidKey = 'workid'

let seconds = 30;
let expires = new Date(new Date() * 1 + seconds * 60000);

export function getToken() {
    return Cookies.get(TokenKey)
}

export function setToken(token) {
    return Cookies.set(TokenKey, token, {expires: expires})
}

export function removeToken() {
    return Cookies.remove(TokenKey)
}

export function getWorkid() {
    return Cookies.get(WorkidKey)
}

export function setWorkid(workid) {
    return Cookies.set(WorkidKey, workid)
}

export function removeWorkid() {
    return Cookies.remove(WorkidKey)
}
