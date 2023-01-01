precision mediump float;

//uniform vec4 u_Color;

// gl 3.x에서 in과 같은 역할
varying vec4 f_Color;

void main()
{
    gl_FragColor = f_Color;
}
