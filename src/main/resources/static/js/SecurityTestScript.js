const form = document.querySelector('#login-form');
const result = document.querySelector('#result');
form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(form);
    const response = await fetch(form.action, {
        method: form.method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(Object.fromEntries(formData))
    });
    const data = await response.json();
    result.innerHTML = JSON.stringify(data, null, 2);
});