precision mediump float;

uniform sampler2D u_TextureUnit;

// gl 3.x에서 in과 같은 역할
varying vec2 f_Texture_Position;

void main()
{
    gl_FragColor = texture2D(u_TextureUnit, f_Texture_Position);
}
