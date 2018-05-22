#version 330

in vec2 texCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;

void main()
{
    fragColor =  vec4(0.0, 1.0, 0.0, 1.0);//texture(texture_sampler, texCoord);
}