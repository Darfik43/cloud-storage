function checkPasswordStrength(passwordInput) {
    var password = passwordInput.value;
    var lengthRequirement = document.getElementById('length-requirement');
    var uppercaseRequirement = document.getElementById('uppercase-requirement');
    var lowercaseRequirement = document.getElementById('lowercase-requirement');
    var numberRequirement = document.getElementById('number-requirement');
    var specialCharacterRequirement = document.getElementById('special-character-requirement');

    if (password.length >= 8) {
        lengthRequirement.classList.remove('text-danger');
        lengthRequirement.classList.add('text-success');
    } else {
        lengthRequirement.classList.remove('text-success');
        lengthRequirement.classList.add('text-danger');
    }

    if (/[A-Z]/.test(password)) {
        uppercaseRequirement.classList.remove('text-danger');
        uppercaseRequirement.classList.add('text-success');
    } else {
        uppercaseRequirement.classList.remove('text-success');
        uppercaseRequirement.classList.add('text-danger');
    }

    if (/[a-z]/.test(password)) {
        lowercaseRequirement.classList.remove('text-danger');
        lowercaseRequirement.classList.add('text-success');
    } else {
        lowercaseRequirement.classList.remove('text-success');
        lowercaseRequirement.classList.add('text-danger');
    }

    if (/\d/.test(password)) {
        numberRequirement.classList.remove('text-danger');
        numberRequirement.classList.add('text-success');
    } else {
        numberRequirement.classList.remove('text-success');
        numberRequirement.classList.add('text-danger');
    }

    if (/[@#$%^&*(),.?":{}|<>!]/.test(password)) {
        specialCharacterRequirement.classList.remove('text-danger');
        specialCharacterRequirement.classList.add('text-success');
    } else {
        specialCharacterRequirement.classList.remove('text-success');
        specialCharacterRequirement.classList.add('text-danger');
    }
}