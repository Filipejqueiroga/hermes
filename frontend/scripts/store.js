const _productCache = {};

document.addEventListener('DOMContentLoaded', async () => {
  if (!auth.requireAuth()) return;

  const content = document.getElementById('store-content');

  try {
    const store = await api.store.getMine();
    if (!store) {
      renderNoStore(content);
    } else {
      renderStoreContent(content, store);
    }
  } catch (err) {
    content.innerHTML = `<div class="alert alert-error">${escapeHtml(err.message)}</div>`;
  }
});

function renderNoStore(content) {
  content.innerHTML = `
    <div class="empty-state">
      <span class="empty-icon">☤</span>
      <h3>Você ainda não possui um espaço no mercado dos deuses</h3>
      <p>Abra sua loja no Olimpo e comece a negociar com os mortais</p>
      <div class="create-store-form">
        <div class="form-group">
          <label>Nome da loja</label>
          <input type="text" id="store-name-input" placeholder="Ex: Forge do Hefesto">
        </div>
        <div class="form-group">
          <label>Descrição <span style="color:var(--muted);font-weight:400;">(opcional)</span></label>
          <input type="text" id="store-desc-input" placeholder="Uma breve apresentação da sua loja">
        </div>
        <div id="create-store-error" class="alert alert-error" style="display:none;margin-bottom:1rem;"></div>
        <button class="btn btn-gold" onclick="createStore()">Criar minha loja</button>
      </div>
    </div>
  `;
}

async function createStore() {
  const name = document.getElementById('store-name-input').value.trim();
  const description = document.getElementById('store-desc-input').value.trim();
  const errorEl = document.getElementById('create-store-error');

  errorEl.style.display = 'none';

  if (!name) {
    errorEl.textContent = 'Dê um nome à sua loja';
    errorEl.style.display = 'block';
    return;
  }

  try {
    const store = await api.store.create(name, description || null);
    renderStoreContent(document.getElementById('store-content'), store);
  } catch (err) {
    errorEl.textContent = err.message || 'Erro ao criar loja';
    errorEl.style.display = 'block';
  }
}

function renderStoreContent(content, store) {
  content.innerHTML = `
    <div class="store-header">
      <div class="store-header-info">
        <h1>Minha Loja</h1>
        <div class="store-subtitle">${escapeHtml(store.name)}</div>
      </div>
      <button class="btn btn-gold" onclick="openProductModal()">+ Adicionar produto</button>
    </div>

    <div id="products-list">
      <div class="loading">Convocando seus produtos...</div>
    </div>

    <div class="modal-overlay" id="product-modal">
      <div class="modal-box">
        <button class="modal-close" onclick="closeProductModal()">✕</button>
        <h3 id="modal-title">Adicionar produto</h3>
        <input type="hidden" id="product-id">
        <div class="form-group">
          <label>Nome</label>
          <input type="text" id="product-name" placeholder="Nome do produto">
        </div>
        <div class="form-group">
          <label>Descrição <span style="color:var(--muted);font-weight:400;">(opcional)</span></label>
          <input type="text" id="product-description" placeholder="Descrição do produto">
        </div>
        <div class="form-group">
          <label>URL da imagem</label>
          <input type="url" id="product-image-url" placeholder="https://...">
        </div>
        <div class="form-group">
          <label>Preço (R$)</label>
          <input type="number" id="product-price" placeholder="0.00" step="0.01" min="0.01">
        </div>
        <div class="form-group">
          <label>Quantidade</label>
          <input type="number" id="product-quantity" placeholder="0" min="0" step="1">
        </div>
        <div id="product-form-error" class="alert alert-error" style="display:none;margin-top:0.5rem;"></div>
        <div class="modal-footer">
          <button class="btn btn-ghost" onclick="closeProductModal()">Cancelar</button>
          <button class="btn btn-gold" onclick="saveProduct()">Salvar</button>
        </div>
      </div>
    </div>
  `;

  document.getElementById('product-modal').addEventListener('click', (e) => {
    if (e.target.id === 'product-modal') closeProductModal();
  });

  loadMyProducts();
}

async function loadMyProducts() {
  const listEl = document.getElementById('products-list');
  try {
    const products = await api.products.getMine();

    if (!products.length) {
      listEl.innerHTML = `
        <div class="empty-state" style="padding:3rem 0;">
          <span class="empty-icon" style="font-size:2.5rem;">📦</span>
          <h3>Nenhum produto ainda</h3>
          <p>Adicione seu primeiro produto ao mercado dos deuses</p>
        </div>
      `;
      return;
    }

    products.forEach(p => { _productCache[p.id] = p; });

    listEl.innerHTML = `
      <div class="table-wrap">
        <table class="products-table">
          <thead>
            <tr>
              <th>Imagem</th>
              <th>Produto</th>
              <th>Descrição</th>
              <th>Preço</th>
              <th>Qtd</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            ${products.map(p => `
              <tr>
                <td>
                  ${p.imageUrl
                    ? `<img src="${escapeHtml(p.imageUrl)}" class="table-image-thumb" alt="${escapeHtml(p.name)}" onerror="this.outerHTML='<div class=\\'table-image-placeholder\\'>☤</div>'">`
                    : `<div class="table-image-placeholder">☤</div>`
                  }
                </td>
                <td><span class="table-product-name">${escapeHtml(p.name)}</span></td>
                <td><span class="table-description">${escapeHtml(p.description || '—')}</span></td>
                <td class="table-price">R$&nbsp;${parseFloat(p.price).toFixed(2)}</td>
                <td>${p.quantity}</td>
                <td>
                  <div class="table-actions">
                    <button class="btn btn-outline btn-sm" onclick="openProductModal(_productCache[${p.id}])">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteProduct(${p.id})">Deletar</button>
                  </div>
                </td>
              </tr>
            `).join('')}
          </tbody>
        </table>
      </div>
    `;
  } catch (err) {
    listEl.innerHTML = `<div class="alert alert-error">${escapeHtml(err.message)}</div>`;
  }
}

function openProductModal(product = null) {
  const errorEl = document.getElementById('product-form-error');
  errorEl.style.display = 'none';

  document.getElementById('product-id').value = '';
  document.getElementById('product-name').value = '';
  document.getElementById('product-description').value = '';
  document.getElementById('product-image-url').value = '';
  document.getElementById('product-price').value = '';
  document.getElementById('product-quantity').value = '';

  if (product) {
    document.getElementById('modal-title').textContent = 'Editar produto';
    document.getElementById('product-id').value = product.id;
    document.getElementById('product-name').value = product.name;
    document.getElementById('product-description').value = product.description || '';
    document.getElementById('product-image-url').value = product.imageUrl || '';
    document.getElementById('product-price').value = product.price;
    document.getElementById('product-quantity').value = product.quantity;
  } else {
    document.getElementById('modal-title').textContent = 'Adicionar produto';
  }

  document.getElementById('product-modal').classList.add('active');
}

function closeProductModal() {
  document.getElementById('product-modal').classList.remove('active');
}

async function saveProduct() {
  const id = document.getElementById('product-id').value;
  const errorEl = document.getElementById('product-form-error');
  errorEl.style.display = 'none';

  const name = document.getElementById('product-name').value.trim();
  const description = document.getElementById('product-description').value.trim();
  const imageUrl = document.getElementById('product-image-url').value.trim();
  const price = parseFloat(document.getElementById('product-price').value);
  const quantity = parseInt(document.getElementById('product-quantity').value, 10);

  if (!name) {
    errorEl.textContent = 'O nome do produto é obrigatório';
    errorEl.style.display = 'block';
    return;
  }
  if (!imageUrl) {
    errorEl.textContent = 'A URL da imagem é obrigatória';
    errorEl.style.display = 'block';
    return;
  }
  if (!price || price <= 0) {
    errorEl.textContent = 'Informe um preço válido (maior que zero)';
    errorEl.style.display = 'block';
    return;
  }
  if (isNaN(quantity) || quantity < 0) {
    errorEl.textContent = 'Informe uma quantidade válida';
    errorEl.style.display = 'block';
    return;
  }

  const data = { name, description: description || null, imageUrl, price, quantity };

  try {
    if (id) {
      await api.products.update(id, data);
    } else {
      await api.products.create(data);
    }
    closeProductModal();
    loadMyProducts();
  } catch (err) {
    errorEl.textContent = err.message || 'Erro ao salvar produto';
    errorEl.style.display = 'block';
  }
}

async function deleteProduct(id) {
  if (!confirm('Tem certeza que deseja remover este produto do mercado dos deuses?')) return;
  try {
    await api.products.delete(id);
    loadMyProducts();
  } catch (err) {
    alert(err.message || 'Erro ao deletar produto');
  }
}
