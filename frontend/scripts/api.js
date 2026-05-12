const API_BASE = 'http://localhost:8080/api/v1';

function escapeHtml(str) {
  return String(str ?? '')
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;');
}

function _token() {
  return localStorage.getItem('token');
}

function _headers(withAuth = false) {
  const h = { 'Content-Type': 'application/json' };
  if (withAuth) h['Authorization'] = `Bearer ${_token()}`;
  return h;
}

async function _handleResponse(res) {
  if (res.status === 401 || res.status === 403) {
    localStorage.removeItem('token');
    localStorage.removeItem('userName');
    sessionStorage.setItem('redirect', window.location.href);
    window.location.href = 'login.html';
    throw new Error('Sessão expirada. Faça login novamente.');
  }
  if (!res.ok) {
    let msg = `Erro ${res.status}`;
    try { const text = await res.text(); if (text) msg = text; } catch { /* ignore */ }
    throw new Error(msg);
  }
  if (res.status === 204) return null;
  return res.json();
}

const api = {
  auth: {
    login(email, password) {
      return fetch(`${API_BASE}/auth/login`, {
        method: 'POST',
        headers: _headers(),
        body: JSON.stringify({ email, password }),
      }).then(_handleResponse);
    },

    register(username, email, password) {
      return fetch(`${API_BASE}/auth/register`, {
        method: 'POST',
        headers: _headers(),
        body: JSON.stringify({ username, email, password }),
      }).then(_handleResponse);
    },
  },

  store: {
    create(name, description) {
      return fetch(`${API_BASE}/store`, {
        method: 'POST',
        headers: _headers(true),
        body: JSON.stringify({ name, description }),
      }).then(_handleResponse);
    },

    getMine() {
      return fetch(`${API_BASE}/store/mine`, {
        headers: _headers(true),
      }).then(async (res) => {
        if (res.status === 404) return null;
        return _handleResponse(res);
      });
    },
  },

  products: {
    getAll() {
      return fetch(`${API_BASE}/products`).then(_handleResponse);
    },

    getMine() {
      return fetch(`${API_BASE}/products/mine`, {
        headers: _headers(true),
      }).then(_handleResponse);
    },

    create(data) {
      return fetch(`${API_BASE}/products`, {
        method: 'POST',
        headers: _headers(true),
        body: JSON.stringify(data),
      }).then(_handleResponse);
    },

    update(id, data) {
      return fetch(`${API_BASE}/products/${id}`, {
        method: 'PUT',
        headers: _headers(true),
        body: JSON.stringify(data),
      }).then(_handleResponse);
    },

    delete(id) {
      return fetch(`${API_BASE}/products/${id}`, {
        method: 'DELETE',
        headers: _headers(true),
      }).then(_handleResponse);
    },
  },
};
