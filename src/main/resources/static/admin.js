const url = 'http://localhost:8080/api/admin';

function getAllUsers() {
    fetch(url)
        .then(response => response.json())
        .then(data => {
            usersTable(data)
        })
}

function usersTable(listAllUsers) {
    let res = '';
    let userRole;

    for (let user of listAllUsers) {
        userRole = rolesToString(user.roles);
        res +=
            `<tr id="row" >
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${userRole}</td>
               <td>
                    <button id="button-edit" class="btn btn-info" type="button"
                    data-bs-toggle="modal" data-bs-target="#editModal"
                    onclick="editModal(${user.id})">Edit</button></td>
                <td>
                    <button class="btn btn-danger" type="button"
                    data-bs-toggle="modal" data-bs-target="#deleteModal"
                    onclick="deleteModal(${user.id})">Delete</button></td>
            </tr>`
    }

    document.getElementById('tableBody').innerHTML = res;

}

function closeModal() {
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click())
}

function rolesToString(roles) {
    let rolesString = '';
    for (let element of roles) {
        rolesString += (element.name.toString().replace('ROLE_', '') + ' ');
    }
    return rolesString;
}

getAllUsers()