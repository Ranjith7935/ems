function updateDesignation() {

    const department = document.getElementById("department").value;
    const designation = document.getElementById("designation");

    designation.innerHTML = ""; // clear previous options

    let options = [];

    if (department === "Development") {
        options = ['Software Engineer', 'Senior Developer', 'Team Lead'];
    } 
    else if (department === "QA & Automation Testing") {
        options = ['QA Engineer', 'Automation Engineer', 'Test Lead'];
    } 
    else if (department === "HR Team") {
        options = ['HR Executive', 'HR Manager', 'Recruiter'];
    } 
    else if (department === "Security") {
        options = ['Security Officer', 'Security Analyst'];
    } 
    else if (department === "Sales & Marketing") {
        options = ['Sales Executive', 'Marketing Manager'];
    }

    // Add default placeholder
    designation.innerHTML = `<option>Select Designation</option>`;

    // append new options dynamically
    options.forEach(function (value) {
        const opt = document.createElement("option");
        opt.value = value;
        opt.textContent = value;
        designation.appendChild(opt);
    });
}


function editRecord(id){
	window.location.href=`/edit-record?id=${id}`;
}


function deleteRecordById(id) {
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            // Redirect to Spring Boot delete mapping
            window.location.href = `/deleteRecord-byId?id=${id}`;
        }
    });
}

function approved(id, type) {
    Swal.fire({
        title: "Are you sure?",
        text: "Do you want to " + type.toLowerCase() + " this request?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes!"
    }).then((result) => {
        if (result.isConfirmed) {

            const form = document.createElement("form");
            form.method = "POST";
            form.action = "/approve-byId";

            const idInput = document.createElement("input");
            idInput.type = "hidden";
            idInput.name = "id";
            idInput.value = id;

            const typeInput = document.createElement("input");
            typeInput.type = "hidden";
            typeInput.name = "type";
            typeInput.value = type;

            form.appendChild(idInput);
            form.appendChild(typeInput);
            document.body.appendChild(form);
            form.submit();
        }
    });
}



