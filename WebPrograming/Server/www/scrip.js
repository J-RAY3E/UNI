async function query(data) {
    try {
        const response = await fetch("/fcgi-bin/server.fcgi", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }

        const result = await response.json();

        return result;

    } catch (error) {
        console.error("Error trying to send the data:", error);
    }
}
function validation(data) {
    const classes = { "x": "float", "y": "int", "rad": "float" };
    const regexInt = /^-?(0|[1-9]\d*)$/;
    const regexFloat = /^-?\d+(\.\d+)?$/;

    function processValue(value, type, key) {
        const strValue = String(value);

        if (type === "int") {
            if (!regexInt.test(strValue)) {
                throw new Error(`Invalid int value for ${key}: ${value}`);
            }
            return parseInt(strValue, 10);
        } else if (type === "float") {
            if (!regexFloat.test(strValue)) {
                throw new Error(`Invalid float value for ${key}: ${value}`);
            }
            return parseFloat(strValue);
        } else {
            throw new Error(`Unknown type: ${type}`);
        }
    }

    for (const [key, type] of Object.entries(classes)) {
        if (data[key] === "" || data[key] == null) {
            throw new Error(`Empty element: ${key}`);
        }

        if (Array.isArray(data[key])) {
            data[key] = data[key].map(val => processValue(val, type, key));
        } else {
            data[key] = processValue(data[key], type, key);
        }
    }

    console.log("Data Validated");
}

function addRowHist(dot,ans){
    const table = document.querySelector("#histTable tbody")
    const row = `
        <tr>
            <th>${dot["x"]}</th>
            <th>${dot["y"]}</th>
            <th>${dot["r"]}</th>
            <th>${ans["result"]}</th>
            <th>${ans["date"]}</th>
            <th>${ans["exec_time"]}</th>
        </tr>
    `
    table.insertAdjacentHTML("beforeend",row);

}

function getDots(data){
    if (!Array.isArray(data["x"])){
        data["x"] = [data["x"]]
    }

    if (!Array.isArray(data["y"])){
        data["y"] = [data["y"]]
    }
    const dots = [];
    for(const x of data["x"]){
        for(const y of data["y"]){
            dots.push({"x":x,"y":y,"r":data["rad"]})
        }
    }
    return dots;
}
function getargs() {
    const form = document.getElementById("form");
    const elements = form.querySelectorAll("input, select, textarea");
    const data = {};

    elements.forEach(element => {
        const name = element.name;
        const type = element.type;

        if (!name) return;

        if (type === "checkbox") {
            if (!Array.isArray(data[name])) {
                data[name] = [];
            }
            if (element.checked) {
                data[name].push(element.value);
            }
        } else if (type === "radio") {
            if (element.checked) {
                data[name] = element.value;
            }
        } else if (type === "select-multiple") {
            data[name] = Array.from(element.selectedOptions).map(opt => opt.value);
        } else {
            data[name] = element.value;
        }
    });

    return data;
}
function send_() {
    try {
        const datos = getargs();
        console.log("Datos recolectados:", datos);
        validation(datos);
        dots = getDots(datos);
        console.log(dots);
        dots.forEach( async dot => {
            const ans = await query(dot)
            console.log(ans);
            addRowHist(dot,ans)
        })

    } catch (error) {
        console.error("Validación fallida:", error.message);
        alert("Error: " + error.message);
    }
}