const changeEvent = new Event('change');

function setInputInvalid(input, feedback, customValidity) {
    console.log('setInputInvalid', input.id);
    if (customValidity) {
        input.setCustomValidity(customValidity);
    }
    input.classList.remove('is-valid');
    input.classList.add('is-invalid');
    if (feedback) {
        feedback.classList.add('d-block');
    }
}

function setInputValid(input, feedback) {
    console.log('setInputValid', input.id);
    input.setCustomValidity('');
    input.classList.remove('is-invalid');
    input.classList.add('is-valid');
    if (feedback) {
        feedback.classList.remove('d-block');
    }
}

function setInputValidInvalid(isValid, input, feedback, customValidity) {
    console.log('setInputValidInvalid', input.id, this);
    if (typeof isValid === 'function') {
        isValid = isValid();
    }
    if (isValid != null) {
        if (isValid) {
            setInputValid(input, feedback);
        } else {
            setInputInvalid(input, feedback, customValidity);
        }
    }
}

function registerInputFeedback(input, feedback) {
    input.addEventListener('change', setInputValidInvalid.bind(null, input.checkValidity.bind(input), input, feedback, undefined));
}

function registerRepeatMatchMainInput(mainInput, repeatInput) {
    mainInput.addEventListener('change', repeatInput.dispatchEvent.bind(repeatInput, changeEvent));
    repeatInput.addEventListener('change', setInputValidInvalid.bind(null,
        () => repeatInput.value ? repeatInput.value === mainInput.value : null,
        repeatInput,
        undefined,
        'repeat'))
}