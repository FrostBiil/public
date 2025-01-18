using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GravityCotroller : MonoBehaviour
{
    private GravityManager gm;
    private void Awake()
    {
        gm = GameObject.Find("Gravity Manager").GetComponent<GravityManager>();
    }
    private Rigidbody rb;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
    }

    void FixedUpdate()
    {
        rb.AddForce(gm.globalGravity * 9.81f); // Replace 9.81 with your desired gravity strength
    }
}