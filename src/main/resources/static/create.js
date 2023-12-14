let newRole = document.querySelector('#roles').selectedOptions;

function createUser() {
    document.getElementById('roles').value

    let roles = []
    for (let i = 0; i < newRole.length; i++) {
        roles.push({
            id: newRole[i].value
        })
    }
    document.getElementById('newUserForm').addEventListener('submit', (e) => {
        e.preventDefault()

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                username: document.getElementById('username').value,
                lastName: document.getElementById('lastName').value,
                age: document.getElementById('age').value,
                password: document.getElementById('password').value,
                roles: roles
            })
        })
            .then((response) => {
                if (response.ok) {
                    document.getElementById('username').value = '';
                    document.getElementById('lastName').value = '';
                    document.getElementById('age').value = '';
                    document.getElementById('password').value = '';
                    document.getElementById('roles').value = '';
                    document.getElementById('users-tab').click()

                    getAllUsers();

                }
            })
    })

}

function loadRoles() {
    let selectAdd = document.getElementById("roles");

    selectAdd.innerHTML = "";

    fetch("http://localhost:8080/api/admin/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.authority.toString().replace('ROLE_', '');
                selectAdd.appendChild(option);
            });
        });
}

window.addEventListener("load", loadRoles);