//function for submit form language in header (reload page after select new language).
function submitLanguageForm(idForm){
    let domForm = document.getElementById(idForm);
    if(!domForm){
        console.log('form not found with id ['+idForm+'].');
        return;
    }
    domForm.submit();
}

//function for research in table before manny character.
function researchTable(target){
    let valueInput = target.value;
    if(valueInput.length < 2 && valueInput.length > 0)
        return;
    clickNext(target);
}

//function for click element next.
function clickNext(target, blockProcess=false){
    if(!blockProcess)
        target.nextSibling.click();
}