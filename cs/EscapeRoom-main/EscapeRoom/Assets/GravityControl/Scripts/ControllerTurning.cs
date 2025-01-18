using UnityEngine;
using UnityEngine.InputSystem;

public class ControllerTurning : MonoBehaviour
{
    [SerializeField]
    private InputActionProperty joystickAction; // Assign the joystick action in the Inspector.

    [SerializeField]
    private float rotationSpeed = 30f; // Adjust the rotation speed as needed.

    private void Update()
    {
        // Read the joystick input value.
        Vector2 joystickInput = joystickAction.action.ReadValue<Vector2>();

        // Rotate the object based on the joystick input.
        RotateObject(joystickInput);
    }

    private void RotateObject(Vector2 input)
    {
        // Calculate the rotation amount based on joystick input.
        float rotationAmount = input.x * rotationSpeed * Time.deltaTime;

        // Apply rotation to the object (you can replace "transform" with your object's transform).
        transform.Rotate(Vector3.up, rotationAmount);
    }
}