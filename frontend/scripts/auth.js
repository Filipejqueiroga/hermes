const auth = {
  save(data) {
    localStorage.setItem('token', data.token);
    localStorage.setItem('userName', data.name || data.email || '');
  },

  getToken() {
    return localStorage.getItem('token');
  },

  getName() {
    return localStorage.getItem('userName');
  },

  remove() {
    localStorage.removeItem('token');
    localStorage.removeItem('userName');
  },

  isLoggedIn() {
    return !!this.getToken();
  },

  logout() {
    this.remove();
    window.location.href = 'index.html';
  },

  requireAuth() {
    if (!this.isLoggedIn()) {
      sessionStorage.setItem('redirect', window.location.href);
      window.location.href = 'login.html';
      return false;
    }
    return true;
  },
};

function _buildNavbar() {
  const loggedIn = auth.isLoggedIn();
  const name = auth.getName();

  const userSection = loggedIn
    ? `<span class="nav-user">Olá, ${escapeHtml(name || 'Viajante')}</span>
       <button class="btn btn-ghost btn-sm" onclick="auth.logout()">Sair</button>`
    : `<a href="login.html" class="btn btn-ghost btn-sm">Login</a>
       <a href="register.html" class="btn btn-outline btn-sm">Cadastro</a>`;

  return `
    <nav class="navbar">
      <div class="navbar-brand">
        <a href="index.html">
          <span class="navbar-logo">☤</span>
          <span class="navbar-name">Hermes</span>
        </a>
      </div>
      <div class="navbar-actions">
        ${userSection}
        <button class="btn btn-ghost btn-sm" onclick="_navGoToStore()">Minha Loja</button>
        <button class="btn btn-gold btn-sm" onclick="_navOpenCart()">Carrinho</button>
      </div>
    </nav>

    <div class="modal-overlay" id="cart-modal">
      <div class="modal-box" style="max-width:420px;">
        <button class="modal-close" onclick="document.getElementById('cart-modal').classList.remove('active')">✕</button>
        <div class="cart-construction">
          <span class="construction-icon">⚡</span>
          <h3>Os deuses ainda estão preparando este espaço</h3>
          <p>O Olimpo está em obras. Volte em breve, viajante.</p>
        </div>
      </div>
    </div>
  `;
}

function _navGoToStore() {
  if (!auth.isLoggedIn()) {
    sessionStorage.setItem('redirect', 'store.html');
    window.location.href = 'login.html';
    return;
  }
  window.location.href = 'store.html';
}

function _navOpenCart() {
  document.getElementById('cart-modal').classList.add('active');
}

document.addEventListener('DOMContentLoaded', () => {
  document.body.insertAdjacentHTML('afterbegin', _buildNavbar());

  document.getElementById('cart-modal').addEventListener('click', (e) => {
    if (e.target.id === 'cart-modal') {
      e.target.classList.remove('active');
    }
  });
});
