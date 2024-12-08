document.addEventListener("DOMContentLoaded", () => {
  const registerForm = document.querySelector("form.spot-submission");
  const spotNameInput = document.getElementById("spotname");
  const locationNameInput = document.getElementById("location");
  const addressInput = document.getElementById("address");
  const hoursInput = document.getElementById("hours");

  // Create error container
  const errorContainer = document.createElement("div");
  errorContainer.className = "error-message";
  registerForm.insertBefore(errorContainer, registerForm.firstChild);

  // Single event listener for form submission
  registerForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    errorContainer.textContent = ""; // Clear previous errors

    const spotData = {
      name: spotNameInput.value.trim(),
      location: locationNameInput.value.trim(),
      address: addressInput.value.trim(),
      hours: hoursInput.value.trim(),
      image: "../img/default-spot.jpg",
    };

    console.log("Submitting spot data:", spotData); // Debug log

    try {
      const response = await fetch("/api/studyspots/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(spotData),
      });

      const data = await response.json();

      if (response.ok) {
        alert("Study spot added successfully!");
        window.location.href = "/map.html";
      } else {
        errorContainer.textContent = data.message || "Error adding study spot";
      }
    } catch (error) {
      console.error("Error:", error);
      errorContainer.textContent =
        "Unable to add spot. Please check your connection and try again.";
    }
  });
});
