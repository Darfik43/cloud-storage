$(document).ready(function () {
    $('#passwordRequirementsBtn').click(function () {
        $('#passwordRequirementsModal').modal('show');
    });

    $('form input').on('input', function () {
        var totalFields = $('form input').length;
        var completedFields = $('form input').filter(function () {
            return $(this).val().trim() !== '';
        }).length;
        var progress = (completedFields / totalFields) * 100;
        $('.progress-bar').css('width', progress + '%').attr('aria-valuenow', progress);
    });
});
$(document).ready(function () {
    $('#togglePassword').click(function () {
        var passwordField = $('#password');
        var passwordFieldType = passwordField.attr('type');
        var toggleIcon = $('#togglePassword i');

        if (passwordFieldType === 'password') {
            passwordField.attr('type', 'text');
            toggleIcon.removeClass('fa-eye-slash').addClass('fa-eye');
        } else {
            passwordField.attr('type', 'password');
            toggleIcon.removeClass('fa-eye').addClass('fa-eye-slash');
        }
    });
});