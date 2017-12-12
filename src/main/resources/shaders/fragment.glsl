#version 330

in vec3 colors;
out vec4 fragColor;

void main()
{
//    fragColor = vec4(0.0, 0.5, 0.25, 1.0);
    fragColor = vec4(colors, 1.0);
}