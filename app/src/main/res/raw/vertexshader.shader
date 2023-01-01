attribute vec3 v_Position;

void main()
{
    gl_Position = vec4(v_Position, 1.0);
    gl_PointSize = 10.0; // point로 그릴경우 point의 크기 지정
}
