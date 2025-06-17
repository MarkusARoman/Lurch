# The Lurch Project

Currently in early development
<br>
## Features
- Window creation
- Continuous & discrete timers
- Shaders Support
- Quad Rendering
- Load Textures
- Cache Textures


## Shader Support
The simplest way to create a Shader object is to input the file path simply: e.g., ``` Shader("name.type") ``` 
| type | extension |
| :---:  | :---: |
| .vert | ``` GL20.GL_VERTEX_SHADER ``` |
| .frag | ``` GL20.GL_FRAGMENT_SHADER ``` |
| .geom | ``` GL32.GL_GEOMETRY_SHADER ``` |
| .tesc | ``` GL40.GL_TESS_CONTROL_SHADER ``` |
| .tese | ``` GL40.GL_TESS_EVALUATION_SHADER ``` |
| .comp | ``` GL43.GL_COMPUTE_SHADER ``` |
<br>
You can also directly input the type, which is slightly faster. <br>
Although the fastest ***way*** is to input the file's source code as a CharSequence.
<br><br>
After creating your Shader object, you can add it to a ShaderProgram. <br>
*Note, this can only be done during instantiation. <br>
This allows you to install or uninstall as needed. <br>
