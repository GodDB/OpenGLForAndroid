// gl 3.x에서 layout과 같은 역할
attribute vec3 v_Position;

uniform mat4 u_Model;
uniform mat4 u_Camera;
uniform mat4 u_Perspective;


void main()
{
    gl_Position = u_Perspective * u_Camera * u_Model * vec4(v_Position, 1.0);
    gl_PointSize = 10.0; // point로 그릴경우 point의 크기 지정
}
