const userUrl = 'http://localhost:8080/api/user';

async function getUserInfo() {
    await fetch(userUrl).then(response => response.json()
        .then(user => getInfo(user)));
}

function getInfo(user) {
    let userInfo = '';
    let userRole = rolesToString(user.roles);

    userInfo += `<tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${userRole}</td>
            </tr>`;
    document.getElementById('userBody').innerHTML = userInfo;
}

function rolesToString(roles) {
    let rolesString = '';
    for (let element of roles) {
        rolesString += (element.name.toString().replace('ROLE_', '') + ' ');
    }
    return rolesString;
}

getUserInfo();