using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FixRigidBodyConstraints : MonoBehaviour
{
    private Rigidbody rb;

    private void Start()
    {
        rb = GetComponent<Rigidbody>();
        rb.centerOfMass = Vector3.zero;
        rb.inertiaTensorRotation = Quaternion.identity;
    }
}
