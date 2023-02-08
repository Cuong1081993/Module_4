class App {
    static showConfirmDialog(message,t) {
        return Swal.fire({
            title: t,
            icon: "warning",
            text: message,
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Vâng, tôi muốn xóa  !"
        });
    };

    static showSuccessAlert(t) {
        Swal.fire({
            position: 'center',
            icon: 'success',
            title: t,
            showConfirmButton: true,
            timer: 5000,
        })
    };

    static showErrorAlert(t) {
        Swal.fire({
            icon: "error",
            title: "Warning",
            text: t,
        });
    };

    static toastSuccess(t) {
        iziToast.success({
            title: 'OK',
            position: 'bottomLeft',
            timeout: 1500,
            message: t
        });
    }

    static toastError(t) {
        iziToast.error({
            title: 'ERROR',
            position: 'bottomLeft',
            timeout: 2000,
            message: t
        });
    }
}