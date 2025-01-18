using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GravityChanger : MonoBehaviour
{

    private GravityManager gm;
    private void Awake()
    {
        gm = GameObject.Find("Gravity Manager").GetComponent<GravityManager>();
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Mouse0))
        {
            Debug.Log("Clicked SHoot");
            Vector3? normal = CastRayAndGetNormal(transform.position, transform.forward, 100.0f);
            if (normal.HasValue)
            {
                gm.globalGravity = normal.Value * -4.91f;
            }
        }
    }

    public Vector3? CastRayAndGetNormal(Vector3 origin, Vector3 direction, float maxDistance)
    {
        RaycastHit hitInfo;

        // Cast the ray
        if (Physics.Raycast(origin, direction, out hitInfo, maxDistance))
        {
            // Return the normal if the ray hits an object
            return hitInfo.normal;
        }

        // Return null if the ray doesn't hit anything
        return null;
    }

}
