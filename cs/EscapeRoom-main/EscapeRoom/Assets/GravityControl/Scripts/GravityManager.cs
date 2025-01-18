using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GravityManager : MonoBehaviour
{
    public Vector3 globalGravity;

    public void Set(Vector3 newGravity)
    {
        globalGravity = newGravity;
    }

    public Vector3 Inverse()
    {
        return globalGravity * (-1);
    }
}
