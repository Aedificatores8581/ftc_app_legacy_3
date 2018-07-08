function file_exists(arg_file)
    local file = io.open(arg_file, "rb")
    if file then file:close() end
    return file ~= nil
end

lines = {}

function lines_from_text_file(file)
    if not file_exists(file) then return {} end
    for line in io.lines(file) do
        lines[#lines + 1] = line
    end
    return lines
end

values = {
    {stv = 33}
}

function parse_file(line_list)
    for i = 1, #line_list do
        -- variable name pass
        for k in string.gmatch(line_list[i], "%s*(.-):") do
            table.insert(values[i], k)
        end
    end
end

function TEST()
    file_lines = lines_from_text_file("test.txt")
    parse_file(file_lines)
end

TEST()
