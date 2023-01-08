precision mediump float;

// gl 3.x에서 in과 같은 역할
uniform vec4 u_Color;

void main()
{
    gl_FragColor = u_Color;
}
