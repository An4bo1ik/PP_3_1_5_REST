const formEditUser = document.getElementById('modalEdit')
let editRole = document.querySelector('#editRole').selectedOptions

async function editModal(id) {
    let editId = `${url}/${id}`;
    loadRolesForEdit();
    await fetch(editId, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(user => {
            document.getElementById('editId').value = user.id;
            document.getElementById('editUsername').value = user.username;
            document.getElementById('editLastName').value = user.lastName;
            document.getElementById('editAge').value = user.age;
            document.getElementById('editPassword').value = user.password;
        })
    });
}

async function editUser() {
    let urlEdit = `${url}/${formEditUser.idEdit.value}`;
    let roles = []
    for (let i = 0; i < editRole.length; i++) {
        roles.push({
            id: editRole[i].value
        })
    }
    let method = {
        method: 'PATCH',
        headers: {"Content-Type": "application/json",
            'Accept': 'application/json'},
        body: JSON.stringify({
            id: formEditUser.idEdit.value,
            username:formEditUser.username.value,
            lastName:formEditUser.lastName.value,
            age:formEditUser.age.value,
            password:formEditUser.password.value,
            roles:roles
        })
    }
    await fetch(urlEdit, method).then(() => {
        closeModal();
        getAllUsers();
    })
}

function loadRolesForEdit() {
    let selectEdit = document.getElementById("editRole");
    selectEdit.innerHTML = "";

    fetch("http://localhost:8080/api/admin/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.authority.toString().replace('ROLE_', '');
                selectEdit.appendChild(option);
            });
        });
}