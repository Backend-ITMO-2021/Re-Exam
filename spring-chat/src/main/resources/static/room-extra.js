let usersList = document.querySelector(".users-list")

async function loadUsers() {
    let response = await fetch(window.location.href + "/load-users")
    if (response.ok) {
        let json = await response.json();
        console.log(json)
        json.forEach(user => {
            let userLi = document.createElement("li")
            userLi.classList.add("list-group-item")
            let liText = document.createTextNode(user.username)
            userLi.appendChild(liText)

            let addForm = document.createElement("form")
            addForm.setAttribute("action", window.location.href + "/add-user")
            addForm.setAttribute("method", "post")

            let addButton = document.createElement("button")
            addButton.setAttribute("type", "submit")
            addButton.appendChild(document.createTextNode("Добавить"))
            addButton.classList.add("btn")
            addButton.classList.add("btn-outline-info")

            let usernameTextInp = document.createElement("input")
            usernameTextInp.setAttribute("name", "username")
            usernameTextInp.setAttribute("value", user.username)
            usernameTextInp.setAttribute("hidden", "hidden")
            addForm.appendChild(usernameTextInp)

            // let csrfInp = document.createElement("input")
            // csrfInp.setAttribute("name", "_csrf")
            // csrfInp.setAttribute("value", "{{_csrf.token}}")
            // addForm.appendChild(csrfInp)

            addForm.appendChild(addButton)
            userLi.appendChild(addForm)
            usersList.appendChild(userLi)
        })
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

// async function addUser(username) {
//     console.log("Add user: " + username)
//     let reqBody = {
//         username,
//     }
//     let response = await fetch(window.location.href + "/add-user", {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json;charset=utf-8'
//         },
//         body: JSON.stringify(reqBody)
//     })
// }

function nameEditorToggle() {
    console.log("Room-extra: nameEditorToggle")
    let editorForm = document.querySelector(".change-name-form")
    editorForm.removeAttribute("hidden")
    let toggleButton = document.querySelector(".name-change-toggle-button")
    toggleButton.setAttribute("hidden", "hidden")
}

let changeNameForm = document.querySelector(".change-name-form")
changeNameForm.setAttribute("action", window.location.href + "/change-name")