function nameEditorToggle() {
    console.log("nameEditorToggle")
    let editorForm = document.querySelector(".change-name-form")
    editorForm.removeAttribute("hidden")
    let toggleButton = document.querySelector(".name-change-toggle-button")
    toggleButton.setAttribute("hidden", "hidden")
}